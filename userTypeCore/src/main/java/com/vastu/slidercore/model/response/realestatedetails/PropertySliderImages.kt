package com.vastu.slidercore.model.response.realestatedetails

data class PropertySliderImages(
    val brochure_slider: List<BrochureSlider>,
    val builder_slider: List<BuilderSlider>,
    val property_slider: List<PropertySlider>
)