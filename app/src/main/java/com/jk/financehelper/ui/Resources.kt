package com.jk.financehelper.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import com.jk.financehelper.R

val monthsArray:Array<String>
    @Composable get(){
        return  stringArrayResource(id = R.array.months)
    }

val daysOfWeekArray:Array<String>
    @Composable get()  {
        return  stringArrayResource(id = R.array.days_of_week)
    }