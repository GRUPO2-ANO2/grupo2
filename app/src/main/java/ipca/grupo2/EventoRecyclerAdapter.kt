package ipca.grupo2

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ipca.grupo2.backend.models.Evento
import ipca.grupo2.backend.models.Utilizador
import ipca.grupo2.backend.tables.BackendUtilizador
import ipca.grupo2.room.AppDatabase
import kotlinx.coroutines.*

class EventoRecyclerAdapter(val eventos: ArrayList<Evento>, val context: Context, var recyclerView: RecyclerView) :
    RecyclerView.Adapter<EventoRecyclerAdapter.ViewHolder>() {
    private lateinit var eventoID: String
    private lateinit var eventoUsers: MutableList<Utilizador>
    private var currentPosition: Int = 0
    private val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            if (currentPosition == itemCount - 1) {
                currentPosition = 0
            } else {
                currentPosition++
            }
            recyclerView.smoothScrollToPosition(currentPosition)
            handler.postDelayed(this, 5000)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        handler.postDelayed(runnable, 5000)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        handler.removeCallbacks(runnable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_eventos,
            parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Retrieve the data for the current position
        holder.data = eventos[position]
        var ImageURL = holder.data.getImage()

        eventoID = holder.data.getId().toString()

        // Set the data to the views
        holder.textViewLocal.text = holder.data.getName()
        var curEventoId = AppDatabase.getDatabase(context)?.eventoDao()?.getCurEventId()?.id
        val mainScope = CoroutineScope(Dispatchers.Main)

        mainScope.launch {
            // User List

            eventoUsers = withContext(Dispatchers.IO)
            {
                BackendUtilizador.getAllUtilizadoresByEvento(eventoID)
            }

         /*   if(eventoUsers.size > 1)
                holder.textViewTotal.text = eventoUsers.size.toString() + " Inscritos"
            else
                holder.textViewTotal.text = eventoUsers.size.toString() + " Inscrito"

            holder.textViewInicio.text = "Inicio:  " + holder.data.getDateStart().toString() */


            Picasso.get().load(ImageURL).resize(360,180).into(holder.imagemEvento)
        }
         if (curEventoId == eventoID){
            holder.buttonEventos.isEnabled = false
            holder.buttonEventos.setBackgroundColor(Color.parseColor("#440123"))
            holder.buttonEventos.text  = "Selecionado"


        }

        holder.imagemEvento.setOnClickListener{
            val bundle = bundleOf("eventoid" to eventoID)
            var navController: NavController? = null
            navController = Navigation.findNavController(holder.itemView)
            navController.navigate(R.id.action_eventosFragment_to_eventoDetalheFragment, bundle)
        }

        holder.buttonEventos.setOnClickListener {
            Singleton.currentID = holder.data.getId().toString()
            eventoID = holder.data.getId().toString()
            getData(){
                var navController: NavController? = null
                navController = Navigation.findNavController(holder.itemView)
                navController!!.navigate(R.id.action_eventosFragment_to_menuFragment2)
            }



        }


    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    // Define the ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var data: Evento

        val textViewLocal : TextView = itemView.findViewById(R.id.nomeEvento)

        // val textViewTotal : TextView = itemView.findViewById(R.id.rowEventoTotalUser)
       // val textViewInicio : TextView = itemView.findViewById(R.id.rowEventoDataInicio)
        val buttonEventos : Button = itemView.findViewById(R.id.rowEventoDown)
        val imagemEvento: ImageView = itemView.findViewById(R.id.imagemMontanha)
    }

    private fun getData(callback:()->Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase(context)!!.eventoDao().joinEvento(eventoID, context)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    context, "Evento downloaded!",
                    Toast.LENGTH_SHORT
                ).show()
                callback.invoke()
            }
        }
    }

}