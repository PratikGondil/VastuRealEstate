package com.vastu.realestate.appModule.dashboard.model

import android.content.Context
import com.vastu.realestate.R

object RealEstateList {
        fun getRealEstateData(context: Context): List<RealEstate>{
            val realEstateList = arrayListOf<RealEstate>()

            val estate1 = RealEstate(
                "1",
                R.drawable.flat_image_1,
                "FFEATURED",
                "ABC",
                "₹ 19.00,000",
                "1 BHK FLATS FOR SALE WITH AM.. 1 Bds - 1 Ba - 700 ft2",
                "BHUSHAN NAGAR AHMA..."
            )

            val estate2 = RealEstate(
                "2",
                R.drawable.flat_image_2,
                "FEATURED",
                "PQR",
                "₹ 28.00,000",
                "2 BHK FLATS FOR SALE WITH AM.. 2 Bds - 1 Ba - 834 ft2",
                "BBHUSHAN NAGAR AHMA..."
            )
            val estate3 = RealEstate(
                "3",
                R.drawable.flat_image_3,
                "FEATURED",
                "XYZ",
                "₹ 12.00,000",
                "1 BHK FLATS FOR SALE WITH AM.. 1 Bds - 1 Ba - 700 ft2",
                "BHUSHAN NAGAR AHMA..."
            )


            realEstateList.add(estate1)
            realEstateList.add(estate2)
            realEstateList.add(estate3)
            realEstateList.add(estate1)
            realEstateList.add(estate2)
            realEstateList.add(estate3)
            realEstateList.add(estate1)
            realEstateList.add(estate2)
            realEstateList.add(estate3)

            return realEstateList

        }
}