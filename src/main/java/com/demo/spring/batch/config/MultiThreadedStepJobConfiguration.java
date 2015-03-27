package com.demo.spring.batch.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.demo.spring.batch.beans.Person;
import com.demo.spring.batch.commons.FileOperationUtils;
import com.demo.spring.batch.listener.ProtocolListener;
import com.demo.spring.batch.operations.LogItemProcessor;
import com.demo.spring.batch.operations.LogItemWriter;
import com.demo.spring.batch.operations.PersonFixedLengthTokenizer;

/**
 * @author Surya Tiwari
 */
@Configuration
public class MultiThreadedStepJobConfiguration {

	private static final Log LOG = LogFactory.getLog(MultiThreadedStepJobConfiguration.class);
	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	private InfrastructureConfiguration infrastructureConfiguration;

	private FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
	
	private String filePath;
	private String fileName;
	
	@Bean
	public Job multiThreadedStepJob() {
		//return jobBuilders.get("multiThreadedStepJob").listener(protocolListener()).start(step()).build();
		return jobBuilders.get("multiThreadedStepJob").listener(new JobExecutionListener() {
			
			public void beforeJob(JobExecution jobExecution) {
				
			}
			
			public void afterJob(JobExecution jobExecution) {
				LOG.info("AfterJob FilePath: " + filePath);
				LOG.info("AfterJob FileName: " + fileName);
				File processedFile = new File(filePath + File.separator + fileName);
				LOG.info(processedFile.getAbsoluteFile());
				if(processedFile.exists()){
					LOG.info("AfterJob File Exists");
					try {
						FileOperationUtils.moveLocalFile(filePath, "C:/Test/SWA/archive", fileName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start(step()).build();
	}

	@Bean
	public Step step() {
		return stepBuilders.get("step")
				.<Person, Person> chunk(10)
				.reader(reader()).processor(processor()).writer(writer())
				.listener(new StepExecutionListener() {
					
					public void beforeStep(StepExecution stepExecution) {
						LOG.info("BeforeStep Status: " + stepExecution.getStatus());
						filePath = "C:/Test/SWA/process";
						fileName = "PersonData.txt";
						reader.setResource(new FileSystemResource(filePath + File.separator + fileName));
					}
					
					public ExitStatus afterStep(StepExecution stepExecution) {
						LOG.info("AfterStep Status: " + stepExecution.getStatus());
						LOG.info("AfterStep FileName: " + fileName);
						return null;
					}
				})
				.taskExecutor(infrastructureConfiguration.taskExecutor()).throttleLimit(4)
				.build();
	}

	@Bean
	public ItemReader<Person> reader() {
		// we read a flat file that will be used to fill a Person object
		//FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		
		// we pass as parameter the flat file directory
		//reader.setResource(new ClassPathResource("PersonData.txt"));
		
		// we use a default line mapper to assign the content of each line to the Person object
		reader.setLineMapper(new DefaultLineMapper<Person>() {
			{
				// we use a custom fixed line tokenizer
				setLineTokenizer(new PersonFixedLengthTokenizer());
				// as field mapper we use the name of the fields in the Person class
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						// we create an object Person
						setTargetType(Person.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		return new LogItemProcessor<Person>();
	}

	@Bean
	public ItemWriter<Person> writer() {
		return new LogItemWriter<Person>();
	}

	@Bean
	public ProtocolListener protocolListener() {
		return new ProtocolListener();
	}
	
}
