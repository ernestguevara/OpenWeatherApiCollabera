package com.ernestguevara.openweatherapicollabera.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}