package com.deras.id.ui.utils


object Constanta {

    const val preferenceName = "Settings"
    const val preferenceDefaultValue = "Not Set"
    const val preferenceDefaultDateValue = "2000/04/30 00:00:00"

    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")


    /* PERMISSION REQUEST CODE */
    const val CAMERA_PERMISSION_CODE = 10
    const val STORAGE_PERMISSION_CODE = 20
    const val LOCATION_PERMISSION_CODE = 30
    const val tempToken =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWZWNkFCelJ6QzFlOE9RckkiLCJpYXQiOjE2NTEwMjE4MzB9.fNi8G9VXnv8Sg1EHJq2KHOeMg_tbhLuo2Hqd6YMacK4"

    const val TAG_AUTH = "TEST_AUTH"
}