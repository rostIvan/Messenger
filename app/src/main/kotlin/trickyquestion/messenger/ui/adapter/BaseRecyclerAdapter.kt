package trickyquestion.messenger.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BaseRecyclerAdapter<M : Any, H : RecyclerView.ViewHolder>
@JvmOverloads
constructor(var itemView: Int? = null,
            var list: MutableList<M>? = null,
            var holder: Class<H>? = null,
            var recyclerBinder: RecyclerBinder<M, H, MutableList<M>>? = null) : RecyclerView.Adapter<H>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): H {
        val view = LayoutInflater.from(parent?.context).inflate(itemView!!, parent, false)
        val clazzConstructor = holder?.getConstructor(View::class.java)
        return clazzConstructor!!.newInstance(view)
    }

    override fun getItemCount(): Int = list!!.size

    override fun onBindViewHolder(holder: H, position: Int) {
        val item = list!![position]
        recyclerBinder?.bind(item, holder, list!!)
    }

    @FunctionalInterface
    interface RecyclerBinder<in M : Any, in H : RecyclerView.ViewHolder, in L : MutableList<*>> {
        fun bind(model: M, holder: H, items: L)
    }

    class Builder<M : Any, H : RecyclerView.ViewHolder> {
        private val adapter: BaseRecyclerAdapter<M, H> = BaseRecyclerAdapter()

        fun itemView(itemView: Int) : Builder<M, H> = builderBlock { adapter.itemView = itemView }
        fun holder(clazz: Class<H>) : Builder<M, H> = builderBlock { adapter.holder = clazz }
        fun items(list: MutableList<M>) : Builder<M, H> = builderBlock { adapter.list = list }
        fun bind(recyclerBinder: RecyclerBinder<M, H, MutableList<M>>) : Builder<M, H>
                = builderBlock { adapter.recyclerBinder = recyclerBinder }

        private fun builderBlock(func: () -> Unit) : Builder<M, H> {
            func.invoke()
            return this
        }

        fun build() : BaseRecyclerAdapter<M, H> = adapter
    }
}