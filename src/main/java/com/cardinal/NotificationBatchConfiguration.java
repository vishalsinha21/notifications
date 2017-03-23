package com.cardinal;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class NotificationBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<UserNotification, UserNotification>chunk(10)
                .reader(reader(dataSource))
                .processor(processor())
                .writer(writer())
                .build();
    }

    private static final String QUERY_FIND_NOTIFICATIONS =
            "SELECT * from user_notification " +
                    "where extract(day from notification_date) = extract(day from '2017-03-11 10:00:00.000')";

    @Bean
    ItemReader<UserNotification> reader(DataSource dataSource) {
        JdbcCursorItemReader<UserNotification> databaseReader = new JdbcCursorItemReader<>();

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(QUERY_FIND_NOTIFICATIONS);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(UserNotification.class));

        return databaseReader;
    }

    @Bean
    public UserNotificationItemProcessor processor() {
        return new UserNotificationItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<UserNotification> writer() {
        FlatFileItemWriter<UserNotification> csvFileWriter = new FlatFileItemWriter<>();

        String exportFilePath = "notification.csv";
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<UserNotification> lineAggregator = createNotificationLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;

    }

    private LineAggregator<UserNotification> createNotificationLineAggregator() {
        DelimitedLineAggregator<UserNotification> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<UserNotification> fieldExtractor = createNotificationFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<UserNotification> createNotificationFieldExtractor() {
        BeanWrapperFieldExtractor<UserNotification> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"userId", "cardId", "notificationDate"});
        return extractor;
    }

}
