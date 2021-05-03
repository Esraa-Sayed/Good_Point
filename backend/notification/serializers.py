from rest_framework import serializers
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from .models import *
from user_account.models import User


class NotificationSerializer(serializers.ModelSerializer):

    class Meta:
        model = Notification
        fields = '__all__'

