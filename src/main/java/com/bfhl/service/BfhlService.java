package com.bfhl.service;

import com.bfhl.config.AppProperties;
import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BfhlService {

    private static final String ALPHA_REGEX = "^[A-Za-z]+$";
    private static final String NUMERIC_REGEX = "^[+-]?\\d+$";

    private final AppProperties appProperties;

    public BfhlService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public BfhlResponse process(BfhlRequest request) {
        if (request == null || request.getData() == null) {
            throw new InvalidRequestException("'data' must be provided as a JSON array");
        }

        List<Object> data = request.getData();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        List<Character> allAlphaCharsInOrder = new ArrayList<>();

        BigInteger sum = BigInteger.ZERO;

        for (Object obj : data) {
            String token = String.valueOf(obj);

            // collect alphabetical characters from every token (even mixed/special)
            for (int i = 0; i < token.length(); i++) {
                char ch = token.charAt(i);
                if (Character.isLetter(ch)) {
                    allAlphaCharsInOrder.add(ch);
                }
            }

            if (token.matches(ALPHA_REGEX)) {
                alphabets.add(token.toUpperCase(Locale.ROOT));
            } else if (token.matches(NUMERIC_REGEX)) {
                BigInteger n = new BigInteger(token);
                if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                    evenNumbers.add(token); // keep original string representation
                } else {
                    oddNumbers.add(token);
                }
                sum = sum.add(n);
            } else {
                specialCharacters.add(token);
            }
        }

        String concatString = buildAlternatingCapsReverse(allAlphaCharsInOrder);

        BfhlResponse response = new BfhlResponse();
        response.setSuccess(true);
        response.setUserId(appProperties.buildUserId());
        response.setEmail(appProperties.getEmail());
        response.setRollNumber(appProperties.getRollNumber());
        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(sum.toString());
        response.setConcatString(concatString);

        return response;
    }

    private String buildAlternatingCapsReverse(List<Character> chars) {
        StringBuilder sb = new StringBuilder();
        int pos = 0; // position in the reversed sequence
        for (int i = chars.size() - 1; i >= 0; i--) {
            char ch = chars.get(i);
            if (pos % 2 == 0) {
                sb.append(Character.toUpperCase(ch));
            } else {
                sb.append(Character.toLowerCase(ch));
            }
            pos++;
        }
        return sb.toString();
    }
}