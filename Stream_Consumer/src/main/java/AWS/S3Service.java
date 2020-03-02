package AWS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3;

	public void download(String bucket_name, String key_name) {
		try (S3Object object = s3.getObject(bucket_name, key_name);
				S3ObjectInputStream s3is = object.getObjectContent();
				FileOutputStream fos = new FileOutputStream(new File(key_name))) {

			byte[] read_buf = new byte[1024];
			int read_len = 0;

			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
