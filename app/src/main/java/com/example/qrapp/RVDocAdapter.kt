package com.example.qrapp

import android.graphics.PixelFormat
import android.support.v4.content.ContextCompat
import java.text.SimpleDateFormat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.qrapp.model.Document
import com.example.qrapp.noise.CustomSurfaceView
import com.example.qrapp.noise.DrawingParams
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.doc_item.view.*

class RVDocAdapter(var docs: List<Document>) : RecyclerView.Adapter<RVDocAdapter.DocViewHolder>() {

    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy")
//    val formatter = SimpleDateFormat("dd.MM.yyyy\nHH:mm:ss")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DocViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.doc_item, p0, false)
        return DocViewHolder(view)
    }

    override fun getItemCount(): Int {
        return docs.size
    }

    override fun onBindViewHolder(p0: DocViewHolder, p1: Int) {
        p0.docName.text = docs[p1].name
        p0.docDateIssue.text = "Дата выдачи\n${formatter.format( parser.parse(docs[p1].issueDate))}"
        p0.docDateExpiration.text = "Дата окончания\n${formatter.format( parser.parse(docs[p1].expirationDate))}"

        /*p0.docNoise.setZOrderOnTop(true)
        p0.docNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(App.context, R.color.colorWhite), DrawingParams()))
        p0.docNoise.holder.setFormat(PixelFormat.TRANSPARENT)*/
        /*p0.docNoise1.setZOrderOnTop(true)
        p0.docNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(App.context, R.color.colorWhite), DrawingParams()))
        p0.docNoise1.holder.setFormat(PixelFormat.TRANSPARENT)*/
    }

    inner class DocViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var docName: TextView = itemView.docName
        var docDateIssue: TextView = itemView.docDateIssue
        var docDateExpiration: TextView = itemView.docDateExpiration
//        var docNoise: SurfaceView = itemView.docNoise
//        var docNoise1: SurfaceView = itemView.docNoise1
    }

}