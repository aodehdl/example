package com.example.demo4.batch;

import com.example.demo4.dto.Member;
import com.example.demo4.flow.MemberProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;


    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("sampleDataStep")
                .<Member, Member> chunk(10)
                .reader(sampleDataReader())
                .processor(new MemberProcessor())
                .writer(sampleDataWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Member> sampleDataReader() {
        FlatFileItemReader<Member> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("sample-data.txt"));
        reader.setLineMapper(new DefaultLineMapper<Member>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name", "age", "gender" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Member>() {{
                setTargetType(Member.class);
            }});
        }});
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Member> sampleDataWriter() {
        JdbcBatchItemWriter<Member> writer = new JdbcBatchItemWriter<>();

        log.debug("{}", writer);
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql("INSERT INTO member (name, age, gender) VALUES (:name, :age, :gender);");
		writer.setDataSource(dataSource);
        return writer;
    }
}
