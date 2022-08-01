package com.example.guru_app

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import com.prolificinteractive.materialcalendarview.*
import android.text.style.StyleSpan



class TodayDecorator: DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(RelativeSizeSpan(1.4f))
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#A115FC")))
    }
}