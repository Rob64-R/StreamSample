package SpringS3Kinesis.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class S3Service {
	
	@Autowired
	private AmazonS3 s3client;

	@Autowired
	Map<String, String> buckets;

	public void downloadFileFromBucket(String bucketName, String keyName) throws Exception {

		try( S3Object object = s3client.getObject(buckets.get(bucketName), keyName);
				S3ObjectInputStream s3is = object.getObjectContent();
				FileOutputStream fos = new FileOutputStream(new File("files/" + bucketName + "/" + keyName));){

			byte[] read_buf = new byte[1024];
			int read_len = 0;

			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
		}
	}

	public void uploadFileToBucket(String bucketName, String keyName) throws Exception {

		File file = new File("files/" + bucketName + "/" + keyName);
		s3client.putObject(new PutObjectRequest(buckets.get(bucketName), keyName, file));

	}
}
