package com.blabberchat.core.services;

import com.blabberchat.core.dtos.NewChannelDTO;
import com.blabberchat.core.models.Channel;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.ChannelRepository;
import com.blabberchat.core.repositories.UserRepository;
import com.blabberchat.core.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChannelService channelService;

    @Test
    @DisplayName("it should create a channel and assign channel to user")
    void createChannel() {
        NewChannelDTO newChannelDTO = MockUtil.getChannelDTO();
        User mockUser = MockUtil.getUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.of(mockUser));
        Mockito.doReturn(null).when(channelRepository).save(Mockito.any(Channel.class));
        Mockito.doReturn(null).when(userRepository).save(Mockito.any(User.class));

        Channel channel = channelService.createChannel(newChannelDTO);

        assertThat(channel.getName()).isEqualTo(newChannelDTO.getName());
        assertThat(channel.getPicture()).isEqualTo(newChannelDTO.getPicture());
        assertThat(channel.getDescription()).isEqualTo(newChannelDTO.getDescription());
        assertThat(channel.getOwner().getUsername()).isEqualTo(mockUser.getUsername());
        assertThat(channel).isEqualTo(mockUser.getChannels().get(0));
    }

    @Test
    @DisplayName("it should throw a NoSuchElementException if user is nonexistent")
    void createChannel_withNonexistentUser() {
        NewChannelDTO newChannelDTO = MockUtil.getChannelDTO();
        User mockUser = MockUtil.getUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> channelService.createChannel(newChannelDTO))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("it should return all channels for a given username")
    void getChannels_withUsername() {
        User mockUser = MockUtil.getUser();
        List<Channel> channels = mockUser.getChannels();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername()))
                .thenReturn(Optional.of(mockUser));

        assertThat(channelService.getChannels(mockUser.getUsername())).isEqualTo(channels);
    }

    @Test
    @DisplayName("it should add user to channel")
    void addUserToChannel() {
        User user = MockUtil.getUser();
        Channel channel = MockUtil.getChannel();

        Mockito.when(channelRepository.findById(channel.getId()))
                .thenReturn(Optional.of(channel));
        Mockito.when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
        Mockito.doReturn(null).when(channelRepository).save(Mockito.any(Channel.class));

        channelService.addUserToChannel(user.getUsername(), channel.getId());

        Set<User> users = channel.getUsers();
        User userInChannel = users.stream().filter(user1 -> user1.getUsername().equals(user.getUsername()))
                .findAny().orElseThrow();

        assertThat(userInChannel).isEqualTo(user);
        assertThat(user.getChannels().stream().findFirst().orElseThrow()).isEqualTo(channel);
    }

    @Test
    @DisplayName("it should throw IllegalStateException if user is already in channel")
    void addUserToChannel_userAlreadyInChannel(){
        User user = MockUtil.getUser_inChannel();
        Channel channel = MockUtil.getChannel();

        Mockito.when(channelRepository.findById(channel.getId()))
                .thenReturn(Optional.of(channel));
        Mockito.when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));


        assertThatThrownBy(() -> channelService.addUserToChannel(user.getUsername(), channel.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User is already in channel");
    }

    @Test
    @DisplayName("it should throw IllegalStateException if owner is already in channel")
    void addUserToChannel_ownerAlreadyInChannel(){
        User user = MockUtil.getUser();
        user.setChannels(new ArrayList<>());
        Channel channel = MockUtil.getChannel();
        channel.setOwner(user);

        Mockito.when(channelRepository.findById(channel.getId()))
                .thenReturn(Optional.of(channel));
        Mockito.when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));


        assertThatThrownBy(() -> channelService.addUserToChannel(user.getUsername(), channel.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User is already in channel");
    }
}