package com.bhargav.controllers;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
	
	OAuthService service;
	Token requestToken;
	String authUrl;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView tryLogin()
	{
		service = new ServiceBuilder()
        .provider(LinkedInApi.class)
        .apiKey("75f7hji0uwbyz8")
        .apiSecret("Dc5k1O4cvdizKuhy")
        .callback("http://localhost:8085/api/redir")
        .build();
		
		requestToken = service.getRequestToken();
		authUrl = service.getAuthorizationUrl(requestToken);
		return new ModelAndView("redirect:"+authUrl);
	}
	
	@RequestMapping(value = "/redir", method = RequestMethod.GET)
	public @ResponseBody String loadLinkedInData(@RequestParam(value = "oauth_token",required=false) String oauth_token,@RequestParam(value = "oauth_verifier",required=false) String oauth_verifier)
	{
		if(oauth_token != null || oauth_verifier != null){
			
			Verifier v = new Verifier(oauth_verifier);
			Token accessToken = service.getAccessToken(requestToken, v);
			OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.linkedin.com/v1/people/~?format=json");
			service.signRequest(accessToken, request);
			Response response = request.send();
			return response.getBody();
		}
		
		else
		{
			return "something bad happened";
		}
	}
}
