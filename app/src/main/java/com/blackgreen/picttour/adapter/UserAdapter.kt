package com.blackgreen.picttour.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blackgreen.picttour.R
import com.blackgreen.picttour.model.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val context: Context, private val userList:ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserName.text = user.userName
        Glide.with(context).load(user.userImage).placeholder(R.drawable.perfil_img).into(holder.imgUser)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val txtUserName:TextView = view.findViewById(R.id.userName)
        val txtTemp:TextView = view.findViewById(R.id.Temp)
        val imgUser:CircleImageView = view.findViewById(R.id.userImage)
    }

}