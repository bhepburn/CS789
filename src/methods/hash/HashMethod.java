package methods.hash;

public abstract class HashMethod {
	
	protected String message;

	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(String message){
		return message;
	}
	
	public abstract byte[] calculateDigest();
	
	
}
