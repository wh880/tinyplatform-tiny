
package org.tinygroup.springmvc.http;

/**
 * Exception thrown from {@link MediaType#parseMediaType(String)} in case of
 * encountering an invalid media type specification String.
 *
 * @author Juergen Hoeller
 * @since 3.2.2
 */
@SuppressWarnings("serial")
public class InvalidMediaTypeException extends IllegalArgumentException {

	private String mediaType;


	/**
	 * Create a new InvalidMediaTypeException for the given media type.
	 * @param mediaType the offending media type
	 * @param msg a detail message indicating the invalid part
	 */
	public InvalidMediaTypeException(String mediaType, String msg) {
		super("Invalid media type \"" + mediaType + "\": " + msg);
		this.mediaType = mediaType;

	}
	/**
	 * Return the offending media type.
	 */
	public String getMediaType() {
		return this.mediaType;
	}

}
