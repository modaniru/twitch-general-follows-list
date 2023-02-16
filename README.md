# Twitch-general-follows-list
#### rest-api application that allows you to get information about:

##### 1. list of shared subscriptions between user A and user B
- **request**
GET /api/v1/twitch/general-following-list?first-user=user1&second-user=user2
- **response**
	  [
		{
			'channel-name': 'username'
			'image-link': 'link'
		},
		...
	  ]
