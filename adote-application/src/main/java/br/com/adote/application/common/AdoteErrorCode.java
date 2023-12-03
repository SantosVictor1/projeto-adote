package br.com.adote.application.common;

public class AdoteErrorCode {
    public static ErrorCode INVALID_CPF = new ErrorCode("ADP_001", "invalid.cpf");
    public static ErrorCode INVALID_EMAIL = new ErrorCode("ADP_002", "invalid.email");
    public static ErrorCode GENERAL_ERROR = new ErrorCode("ADP_003", "general.error");

    public static ErrorCode LEGAL_PERSON_TYPE = new ErrorCode("ADP_004", "legal.person.type");
    public static ErrorCode METHOD_ARGUMENT_INVALID = new ErrorCode("ADP_005", "method.argument.invalid");
    public static ErrorCode MUST_NOT_BE_EMPTY = new ErrorCode("ADP_006", "must.not.be.empty");
    public static ErrorCode MUST_NOT_BE_NULL = new ErrorCode("ADP_007", "must.not.be.null");
    public static ErrorCode PERSON_ALREADY_EXISTS = new ErrorCode("ADP_008", "person.already.exists");
    public static ErrorCode PERSON_NOT_FOUND = new ErrorCode("ADP_009", "person.not.found");
    public static ErrorCode PET_NOT_FOUND = new ErrorCode("ADP_010", "pet.not.found");
}
