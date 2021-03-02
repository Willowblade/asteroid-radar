package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.main.AsteroidSelection
import java.lang.Exception

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.asteroid_hazardous_icon)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context.getString(R.string.safe_asteroid_icon)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.safe_asteroid)
    }
}

@BindingAdapter("asteroidContentDescription")
fun bindRecycleViewToAsteroid(constrainedLayout: ConstraintLayout, asteroid: Asteroid) {
    constrainedLayout.contentDescription = "Asteroid ${asteroid.codename} will pass at ${asteroid.closeApproachDate}, confirm for details."
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter(value = ["listData", "filter"])
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?, asteroidSelection: AsteroidSelection) {
    data?.let {
        val adapter = recyclerView.adapter as AsteroidAdapter
        val nextWeekFormattedDates = getNextSevenDaysFormattedDates()
        val today = getToday()
        // Did the filtering here, but I don't know if this is the best spot. I didn't really validate that this
        // filtering could be done in the ViewModel, since the Transformation only takes a single LiveData
        adapter.submitList(data.filter {

            when (asteroidSelection) {
                AsteroidSelection.NEXT_WEEK -> {
                    return@filter nextWeekFormattedDates.contains(it.closeApproachDate)
                }
                AsteroidSelection.ALL -> {
                    return@filter true
                }
                AsteroidSelection.TODAY -> {
                    return@filter it.closeApproachDate == today
                }
                else -> throw Exception("Impossible asteroid selection value")
            }
        })
    }
}

@BindingAdapter("imgUrl")
fun bindImageViewToPictureOfDayUrl(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        if (pictureOfDay.mediaType != "image") {
            return@let
        }

        val context = imageView.context
        val picasso = Picasso.Builder(context).build()
        picasso.load(pictureOfDay.url).into(imageView)
    }
}

@BindingAdapter("imgTitle")
fun bindImageViewToPictureOfDayTitle(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        imageView.contentDescription = pictureOfDay.title
    }
}