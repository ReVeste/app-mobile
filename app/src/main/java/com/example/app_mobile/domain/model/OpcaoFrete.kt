package com.example.app_mobile.domain.model

data class OpcaoFrete(
    val id: Int,
    val name: String,
    val price: String,
    val custom_price: String,
    val discount: String,
    val currency: String,
    val delivery_time: Int,
    val delivery_range: DeliveryRange,
    val custom_delivery_time: Int,
    val custom_delivery_range: DeliveryRange,
    val packages: List<PackageInfo>,
    val additional_services: AdditionalServices,
    val additional: Additional,
    val company: Company,
    val error: String
)

data class DeliveryRange(
    val min: Int,
    val max: Int
)

data class PackageInfo(
    val price: String,
    val discount: String,
    val format: String,
    val weight: String,
    val insurance_value: String,
    val dimensions: Dimensions
)

data class Dimensions(
    val height: Int,
    val width: Int,
    val length: Int
)

data class AdditionalServices(
    val receipt: Boolean,
    val own_hand: Boolean,
    val collect: Boolean
)

data class Additional(
    val unit: UnitCost
)

data class UnitCost(
    val price: Int,
    val delivery: Int
)

data class Company(
    val id: Int,
    val name: String,
    val picture: String
)