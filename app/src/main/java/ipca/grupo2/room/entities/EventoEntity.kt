package ipca.grupo2.room.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/*
    Not using Backend models, instead creating
    new classes making them entities to use in
    room.
*/

@Entity
data class EventoEntity(
    @PrimaryKey
    @NonNull
    val id: String,
    val idGuia: String,
    val location: String,
    val dateStart: String,
    val dateFinish: String,
    val image: String,
    val dem: Int,
    val elevation: Int?,
    val latitude: Float,
    val longitude: Float,
    val name: String,
    val registrations: Int
)