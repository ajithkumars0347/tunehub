package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	@Autowired
	PlaylistService playlistService; 
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songlist= songService.fetchAllSongs();
		model.addAttribute("songs",songlist);
		return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);
		System.out.println(playlist);
		List<Song> songlist=playlist.getSongs();
		for(Song s:songlist) {
			s.getPlayLists().add(playlist);
			songService.updateSong(s);
		}
		return "adminHome";
	}
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		List<Playlist> allPlaylists=playlistService.fetchAllPlaylists();
	 model.addAttribute("allPlaylists",allPlaylists);
	return "displayPlaylists";
	}
}
