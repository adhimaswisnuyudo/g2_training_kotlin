package com.g2academy.training.models

import com.google.gson.annotations.SerializedName

data class ResponseMember(


	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("islogin")
	val islogin: Boolean? = null,

	@field:SerializedName("memberid")
	val memberid: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
