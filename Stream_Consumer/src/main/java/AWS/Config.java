package AWS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class Config {
	@Value("${amazonProperties.region}")
    private Regions region;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    
    @Bean
    public AmazonS3 s3client() {
		
    	AmazonS3 s3 = AmazonS3ClientBuilder
        		.standard()
        		.withRegion(region)
        		.build();
		
		return s3;
	}

}
