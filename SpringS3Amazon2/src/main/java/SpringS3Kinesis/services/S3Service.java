package SpringS3Kinesis.services;

public interface S3Service {
	public void downloadFileFromBucket(String bucketName, String keyName) throws Exception;

	public void uploadFileToBucket(String bucketName, String keyName) throws Exception;
}
