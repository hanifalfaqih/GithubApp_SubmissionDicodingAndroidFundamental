package id.allana.githubapp_bfaa.data.model.network

import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(

	@field:SerializedName("bio")
	val bio: Any? = null,

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("html_url")
	val urlProfile: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

)
