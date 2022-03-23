package com.g2academy.training.networks

class Myconst {
    companion object{
        const val BASE_URL = "http://103.108.190.71/api/"
        const val DEFAULT_HEADER = "SECRET:abc"
        const val CONTENT_TYPE = "Content-Type:application/json"

        const val EP_LOGIN = "auth/login"
        const val EP_REGISTER = "auth/register"

        const val EP_ALLPRODUCTS = "products/all"
        const val EP_ADDPRODUCT = "products/add"
        const val EP_DELETEPRODUCT = "products/delete"

        const val SP_LOGINDATA = "LOGINDATA"
    }
}