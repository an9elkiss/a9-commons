package com.an9elkiss.commons.command;

public interface Status {

	Integer SUCCESS_CODE = 200;
	String SUCCESS_MESSAGE = "�����ɹ�";

	Integer ACCESS_DENY_CODE = 500;
	String ACCESS_DENY_MESSAGE = "�ܾ�����";

	Integer getCode();

	String getMessage();

}
