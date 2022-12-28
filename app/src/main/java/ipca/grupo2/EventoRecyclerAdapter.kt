package ipca.grupo2

import android.app.PendingIntent.getActivity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ipca.grupo2.backend.models.Evento

class EventoRecyclerAdapter(val eventos: ArrayList<Evento>, val context: Context) :
    RecyclerView.Adapter<EventoRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_eventos,
            parent,false);
        return ViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Retrieve the data for the current position
        val data = eventos[position];

        holder.id = data.getId()
        holder.textViewLocal.setOnClickListener {




        }


        // Debug purposes again
        Log.d("eventoAdapter", holder.textViewLocal.text.toString() + " text");

        // Set the data to the views
        holder.textViewLocal.text = data.getLocation();
    }

    override fun getItemCount(): Int {
        return eventos.size;
    }

    // Define the ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewLocal : Button = itemView.findViewById(R.id.local);
        var id : String? =  null;
    }
}