package Adapter

import DataBase.DBNotes
import Model.Notes
import MyInterface.InterfaceForClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todonotes.R

class NotesAdapter(var list: ArrayList<DBNotes>, var interfaceForClickListener: InterfaceForClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder  {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_view, parent,false)
        return ViewHolder(view)    // new nhi OK...
    }

    override fun getItemCount(): Int {
        return list.size   // In Java we use list.get(position)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {

        var notesDB = list[position]
        holder.Title.text = notesDB.title
        holder.Desc.text = notesDB.desc
        holder.checkBox.isChecked = notesDB.isTaskCompleted
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                interfaceForClickListener.onClick(notesDB)
            }
        })
        holder.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notesDB.isTaskCompleted = isChecked
                interfaceForClickListener.onUpdate(notesDB)
                //holder.checkBox.isChecked = notesDB.isTaskCompleted
            }
        })
        //Glide.with(holder.itemView).load(notesDB.imagePath).into(holder.myImage)
        Glide.with(holder.itemView).load(notesDB.imagePath).circleCrop().into(holder.myImage)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Title : TextView = itemView.findViewById(R.id.customTitleID)
        var Desc : TextView = itemView.findViewById(R.id.customDescID)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkboxID)
        var myImage: ImageView = itemView.findViewById(R.id.imageID)
    }

}