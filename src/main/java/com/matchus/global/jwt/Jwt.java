package com.matchus.global.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public final class Jwt {

	private final String issuer;

	private final String clientSecret;

	private final int expirySeconds;

	private final Algorithm algorithm;

	private final JWTVerifier jwtVerifier;

	public Jwt(String issuer, String clientSecret, int expirySeconds)
		throws UnsupportedEncodingException {
		this.issuer = issuer;
		this.clientSecret = clientSecret;
		this.expirySeconds = expirySeconds;
		this.algorithm = Algorithm.HMAC512(clientSecret);
		this.jwtVerifier = com.auth0.jwt.JWT
			.require(algorithm)
			.withIssuer(issuer)
			.build();
	}

	public String sign(Claims claims) {
		Date now = new Date();
		JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
		builder.withIssuer(issuer);
		builder.withIssuedAt(now);
		if (expirySeconds > 0) {
			builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
		}
		builder.withClaim("username", claims.username);
		builder.withArrayClaim("roles", claims.roles);
		return builder.sign(algorithm);
	}

	public Claims verify(String token) throws JWTVerificationException {
		return new Claims(jwtVerifier.verify(token));
	}

	public String getIssuer() {
		return issuer;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public int getExpirySeconds() {
		return expirySeconds;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public JWTVerifier getJwtVerifier() {
		return jwtVerifier;
	}

	static public class Claims {

		String username;
		String[] roles;
		Date iat;
		Date exp;

		protected Claims() {
		}

		Claims(DecodedJWT decodedJWT) {
			Claim username = decodedJWT.getClaim("username");
			if (!username.isNull()) {
				this.username = username.asString();
			}
			Claim roles = decodedJWT.getClaim("roles");
			if (!roles.isNull()) {
				this.roles = roles.asArray(String.class);
			}
			this.iat = decodedJWT.getIssuedAt();
			this.exp = decodedJWT.getExpiresAt();
		}

		public static Claims from(String username, String[] roles) {
			Claims claims = new Claims();
			claims.username = username;
			claims.roles = roles;
			return claims;
		}

	}

}
