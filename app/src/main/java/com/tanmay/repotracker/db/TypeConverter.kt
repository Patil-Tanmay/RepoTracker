package com.tanmay.repotracker.db

import androidx.room.TypeConverter
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tanmay.repotracker.data.License
import com.tanmay.repotracker.data.Owner

class TypeConverter {

    @TypeConverter
    fun LicenceToJson(license: License) : String {
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<License> = moshi.adapter(License::class.java)
        return jsonAdapter.toJson(license)
    }

    @TypeConverter
    @FromJson
    fun JsonToLicense(string : String) : License? {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<License> = moshi.adapter(License::class.java)
        return jsonAdapter.fromJson(string)
    }

    @TypeConverter
    fun OwnerToJson(owner: Owner) : String{
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Owner> = moshi.adapter(Owner::class.java)
        return jsonAdapter.toJson(owner)
    }

    @TypeConverter
    fun JsontoOwner(string:String) : Owner?{
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Owner> = moshi.adapter(Owner::class.java)
        return  jsonAdapter.fromJson(string)
    }

    @TypeConverter
    fun AnyToString(item : Any) : String{
        return item.toString()
    }

    @TypeConverter
    fun StringToAny(string :String) : Any{
        return string.any()
    }

    @TypeConverter
    fun ListANyToString(list:List<Any>):String{
        return list.toString()
    }

    @TypeConverter
    fun StringTOListANy(string :String):List<Any>{
        return string.toList()
    }




}