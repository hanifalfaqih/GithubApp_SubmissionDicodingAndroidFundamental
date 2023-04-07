package id.allana.githubapp_bfaa.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.allana.githubapp_bfaa.data.model.local.User
import id.allana.githubapp_bfaa.databinding.ListItemUserBinding

class ListUserFavoriteAdapter: RecyclerView.Adapter<ListUserFavoriteAdapter.ListUserFavoriteViewHolder>() {

    private var listItem: MutableList<User> = mutableListOf()


    fun setListItems(listItem: List<User>) {
        clearListItems()
        addListItems(listItem)
    }

    private fun clearListItems() {
        this.listItem.clear()
    }

    private fun addListItems(listItem: List<User>) {
        this.listItem.addAll(listItem)
    }

    class ListUserFavoriteViewHolder(private val binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: User) {
            with(item) {
                binding.tvName.text = this.username
                binding.tvUrlProfile.text = this.profileUrl
                Glide.with(itemView)
                    .load(this.avatarUrl)
                    .into(binding.imgAvatar)
                itemView.setOnClickListener {
                    val actionDetail = ListUserFavoriteFragmentDirections.actionListUserFavoriteFragmentToDetailUserFragment(this.username)
                    it.findNavController().navigate(actionDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserFavoriteViewHolder {
        val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserFavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ListUserFavoriteViewHolder, position: Int) {
        holder.bindView(listItem[position])
    }
}