package com.demo.spring.batch.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.spring.batch.config.MultiThreadedStepJobConfiguration;
import com.demo.spring.batch.config.StandaloneInfrastructureConfiguration;


/**
 * @author Surya Tiwari
 */
@ContextConfiguration(classes = { StandaloneInfrastructureConfiguration.class, MultiThreadedStepJobConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class MultithreadedStepJobTests {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private JobRepository jobRepository;

	@Test
	public void testLaunchJob() throws Exception {
		
		JobParameters jobParameters = new JobParametersBuilder().addString("Launched:", new Date().toString()).toJobParameters();
		jobLauncher.run(job, jobParameters);
		assertThat(jobRepository.getLastJobExecution("multiThreadedStepJob", jobParameters).getExitStatus(), is(ExitStatus.COMPLETED));
	}

}
