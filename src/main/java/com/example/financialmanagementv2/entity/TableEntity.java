package com.example.financialmanagementv2.entity;

public record TableEntity<T>(int code,String msg,long count,T data) {
}
