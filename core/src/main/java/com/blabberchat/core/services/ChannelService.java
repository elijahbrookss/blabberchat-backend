package com.blabberchat.core.services;


import com.blabberchat.core.dtos.NewChannelDTO;
import com.blabberchat.core.models.Channel;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.ChannelRepository;
import com.blabberchat.core.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @PreAuthorize("(hasAuthority('USER_ROLE') or hasAuthority('ADMIN_ROLE')) and #channelDTO.getOwnerUsername() == authentication.principal")
    public Channel createChannel(NewChannelDTO channelDTO) {
        log.info("Creating new channel: {}, owner: {} ", channelDTO.getName(), channelDTO.getOwnerUsername());

        User user = userRepository.findByUsername(channelDTO.getOwnerUsername()).orElseThrow();

        Channel channel = Channel.builder()
                .picture(channelDTO.getPicture())
                .name(channelDTO.getName())
                .description(channelDTO.getDescription())
                .owner(user)
                .createdAt(LocalDateTime.now())
                .users(Set.of())
                .build();

        channelRepository.save(channel);
        addChannelToUser(user, channel);
        return channel;
    }

    @PreAuthorize("hasAuthority('ADMIN_ROLE') or (hasAuthority('USER_ROLE') and #username == authentication.principal)")
    public List<Channel> getChannels(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getChannels();
    }

    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public List<Channel> getChannels() {
        return channelRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN_ROLE') or (hasAuthority('USER_ROLE') and #username == authentication.principal)")
    public void addUserToChannel(String username, String channelId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        // Check if user is already in channel.
        channel.getUsers().stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst().ifPresent(u -> {
                    log.info("User, {}, is already in channel, {}", username, channel.getName());
                    throw new IllegalStateException("User is already in channel");
                });
        if (username.equals( channel.getOwner().getUsername() )) {
            log.info("User, {}, is already in channel, {}", username, channel.getName());
            throw new IllegalStateException("User is already in channel");
        }

        log.info("Adding user: {} to channel: {}", user.getUsername(), channel.getName());
        channel.getUsers().add(user);

        addChannelToUser(user, channel);
        channelRepository.save(channel);
    }

    private void addChannelToUser(User user, Channel channel) {
        List<Channel> channels = user.getChannels();
        channels.add(channel);
        user.setChannels(channels);

        userRepository.save(user);
    }
}
