package com.blabberchat.core.controllers;


import com.blabberchat.core.dtos.NewChannelDTO;
import com.blabberchat.core.models.Channel;
import com.blabberchat.core.services.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.blabberchat.core.util.UtilClass.getUri;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping()
    public ResponseEntity<Channel> createChannel(@RequestBody @Valid NewChannelDTO channelDTO){
        return ResponseEntity.created(getUri("/api/channels"))
                .body(channelService.createChannel(channelDTO));
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<Channel> getChannel(@PathVariable String channelId) {
        return ResponseEntity.ok().body(channelService.getChannel(channelId));
    }

    @GetMapping()
    public ResponseEntity<List<Channel>> getAllChannels() {
        return ResponseEntity.ok().body(channelService.getAllChannels());
    }

    @PostMapping("/{channelId}/join")
    public ResponseEntity<Object> addUserToChannel(
            @RequestHeader String username,
            @PathVariable String channelId
    ) {
        channelService.addUserToChannel(username, channelId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Channel>> getUserChannels(@PathVariable String username) {
        return ResponseEntity.ok().body(channelService.getChannels(username));
    }
}
