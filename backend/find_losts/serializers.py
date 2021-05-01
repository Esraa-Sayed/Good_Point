from rest_framework import serializers
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from .models import *
from user_account.models import User


class LostObjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = LostObject
        fields = ['id', 'date', 'city', 'is_matched', 'user_id']



class LostItemSerializer(serializers.ModelSerializer):

    class Meta:
        model = LostItem
        fields = ['id', 'type', 'serial_number', 'brand', 'color', 'description', 'image']

    def validate(self, attrs):
        if LostPerson.objects.filter(id=attrs.get('id', '')).exists():
            raise serializers.ValidationError({'id': {'id already exists'}})
        return super().validate(attrs)



class LostPersonSerializer(serializers.ModelSerializer):

    class Meta:
        model = LostPerson
        fields = ['id', 'name']

    def validate(self, attrs):
        if LostItem.objects.filter(id=attrs.get('id', '')).exists():
            raise serializers.ValidationError({'id': {'id already exists'}})
        return super().validate(attrs)

class LostPersonImageSerializer(serializers.ModelSerializer):

    class Meta:
        model = LostPersonImage
        fields = ['id', 'image']