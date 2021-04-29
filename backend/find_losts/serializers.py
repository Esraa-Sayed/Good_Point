from rest_framework import serializers
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from .models import LostObject, LostItem
from user_account.models import User


class LostObjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = LostObject
        fields = ['id', 'date', 'city', 'is_matched', 'user_id']



class LostItemSerializer(serializers.ModelSerializer):

    class Meta:
        model = LostItem
        fields = ['id', 'type', 'serial_number', 'brand', 'color', 'description', 'image']
