/**
 * 
 * @author PTrXuan
 * Created on Sep 24, 2021
 */
package com.vti.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a TokenUser
 *
 *
 * @author PTrXuan Created on Sep 24, 2021
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class TokenUser {

	private String token;

}
