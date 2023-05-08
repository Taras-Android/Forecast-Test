package com.example.forecasttest.forecast.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.forecasttest.R
import com.example.forecasttest.databinding.ForecastDayCardBinding
import kotlin.math.roundToInt

class
ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var data: List<ForecastDayViewState> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_day_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(productList: List<ForecastDayViewState>) {
        this.data = productList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecastDayViewState: ForecastDayViewState) {
            val bind = ForecastDayCardBinding.bind(itemView)
            val res = itemView.resources
            bind.apply {
                viewAvgTemp.text = res.getString(R.string.avg_temperature, forecastDayViewState.avgTemp.roundToInt())
                viewMinTemp.text = res.getString(R.string.min_temperature, forecastDayViewState.minTemp.roundToInt())
                viewMaxTemp.text = res.getString(R.string.max_temperature, forecastDayViewState.maxTemp.roundToInt())
                viewForecastDayDescription.text = forecastDayViewState.description
                viewDate.text = forecastDayViewState.date

                Glide.with(weatherImage)
                    .asBitmap()
                    .load("https:${forecastDayViewState.icon}")
                    .into(BitmapImageViewTarget(weatherImage))
            }
        }
    }
}