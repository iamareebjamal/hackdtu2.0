package er.health.help.data.model

data class DiseasePrediction(val predictions: Map<String, Float>, var cures: List<String>?)
