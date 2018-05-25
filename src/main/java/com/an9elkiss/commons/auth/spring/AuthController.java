package com.an9elkiss.commons.auth.spring;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.an9elkiss.commons.command.ApiResponseCmd;

@Controller
public class AuthController {

	@RequestMapping(value = "/access-deny", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResponseCmd> getWeekDays() {

		return ResponseEntity.ok(ApiResponseCmd.deny());
	}

}
