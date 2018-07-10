package com.oraclechain.eosio.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oraclechain.eosio.dto.MessageResult;
import com.oraclechain.eosio.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class EosErrorUtils {

    public static MessageResult handleEosResponse(String contents, String component_name) throws Exception {


        //fc层异常
        JSONObject fcJsonObject = (JSONObject) JSON.parse(contents);
        Integer fc_code_int = (Integer) fcJsonObject.get("code");
        if (fc_code_int != null) {
            //chain层异常
            String fc_error_string = JSON.toJSONString(fcJsonObject.get("error"));
            JSONObject chainJsonObject = (JSONObject) JSON.parse(fc_error_string);
            Integer chain_code_int = (Integer) chainJsonObject.get("code");
            if (chain_code_int != null) {
                log.info("--->" + component_name + " failed:" + contents);
                String chain_msg_string = ErrorCodeEnumChain.getMsgById(chain_code_int);
                return MessageResult.error(chain_msg_string, chain_code_int, "");
            }
            log.info("--->" + component_name + " failed:" + contents);
            String fc_msg_string = ErrorCodeEnumFC.getMsgById(fc_code_int);
            return MessageResult.error(fc_msg_string, fc_code_int, "");
        }

        log.info("--->" + component_name + " success:" + fcJsonObject.toString());
        return MessageResult.success(fcJsonObject);
    }
}
