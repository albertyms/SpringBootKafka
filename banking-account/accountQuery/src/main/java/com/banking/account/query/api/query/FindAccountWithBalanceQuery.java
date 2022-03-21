package com.banking.account.query.api.query;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

    private double balance;
    private EqualityType equalityType;

}