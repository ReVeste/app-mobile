package com.example.app_mobile.domain.model

import java.math.BigDecimal

data class Preference(
    val id: String,
    val items: List<PreferenceItem>?,
    val payer: PreferencePayer?,
    val clientId: String?,
    val paymentMethods: PreferencePaymentMethods?,
    val backUrls: PreferenceBackUrls?,
    val shipments: PreferenceShipments?,
    val notificationUrl: String?,
    val statementDescriptor: String?,
    val externalReference: String?,
    val expires: Boolean?,
    val dateOfExpiration: String?,
    val expirationDateFrom: String?,
    val expirationDateTo: String?,
    val collectorId: Long?,
    val marketplace: String?,
    val marketplaceFee: BigDecimal?,
    val additionalInfo: String?,
    val autoReturn: String?,
    val operationType: String?,
    val differentialPricing: PreferenceDifferentialPricing?,
    val processingModes: List<String>?,
    val binaryMode: Boolean?,
    val taxes: List<PreferenceTax>?,
    val tracks: List<PreferenceTrack>?,
    val metadata: Map<String, Any>?,
    val initPoint: String?,
    val sandboxInitPoint: String?,
    val dateCreated: String?
)

data class PreferenceItem(
    val id: String,
    val title: String,
    val description: String?,
    val pictureUrl: String?,
    val categoryId: String?,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val currencyId: String?
)

data class PreferencePayer(
    val name: String,
    val surname: String?,
    val email: String,
    val phone: Phone?,
    val identification: Identification?,
    val address: Address?,
    val dateCreated: String?,
    val lastPurchase: String?
)

data class Phone(
    val areaCode: String?,
    val number: String?
)

data class Identification(
    val type: String?,
    val number: String?
)

data class Address(
    val zipCode: String?,
    val streetName: String?,
    val streetNumber: String?,
    val floor: String?,
    val apartment: String?,
    val cityName: String?,
    val stateName: String?,
    val countryName: String?
)

data class PreferencePaymentMethods(
    val excludedPaymentTypes: List<ExcludedType>?,
    val excludedPaymentMethods: List<ExcludedType>?,
    val defaultPaymentMethodId: String?,
    val installments: Int?,
    val defaultInstallments: Int?
)

data class ExcludedType(
    val id: String?
)

data class PreferenceBackUrls(
    val success: String?,
    val failure: String?,
    val pending: String?
)

data class PreferenceShipments(
    val mode: String?,
    val localPickup: Boolean?,
    val dimensions: String?,
    val defaultShippingMethod: String?,
    val freeMethods: List<String>?,
    val cost: BigDecimal?,
    val freeShipping: Boolean?,
    val receiverAddress: Address?,
    val expressShipment: Boolean?
)

data class PreferenceDifferentialPricing(
    val id: Int?
)

data class PreferenceTax(
    val type: String?,
    val value: BigDecimal?
)

data class PreferenceTrack(
    val type: String?,
    val values: Map<String, String>?
)