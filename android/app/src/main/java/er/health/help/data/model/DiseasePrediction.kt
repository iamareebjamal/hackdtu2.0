package er.health.help.data.model

data class DiseasePrediction(val predictions: Map<String, Float> = HashMap(), var cures: List<String> = ArrayList())
