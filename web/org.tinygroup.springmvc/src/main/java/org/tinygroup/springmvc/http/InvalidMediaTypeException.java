/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
