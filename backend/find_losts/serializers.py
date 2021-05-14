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
        fields = '__all__'



class FoundObjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundObject
        fields = ['date', 'longitude', 'latitude', 'city', 'user_id', 'is_matched']

class FoundItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundItem
        fields = ['id', 'type', 'color', 'brand', 'description', 'serial_number', 'image']

    def validate(self, attrs):
        if FoundPerson.objects.filter(id=attrs.get('id', '')).exists():
            raise serializers.ValidationError({'id': {'id already exists'}})
        return super().validate(attrs)


class FoundPersonSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundPerson
        fields = ['id', 'name']

    def validate(self, attrs):
        if FoundItem.objects.filter(id=attrs.get('id', '')).exists():
            raise serializers.ValidationError({'id': {'id already exists'}})
        return super().validate(attrs)


class FoundPersonImageSerializer(serializers.ModelSerializer):
    class Meta:
        model = FoundPersonImage
        fields = ['id_image', 'image']

class MapSerializer(serializers.ModelSerializer):
    longitude = serializers.DecimalField(max_digits=14, decimal_places=10, source='id.longitude')
    latitude = serializers.DecimalField(max_digits=14, decimal_places=10, source='id.latitude')
    user_id = serializers.IntegerField(source='id.user_id.id')

    class Meta:
        model = FoundItem
        fields = ['longitude', 'latitude', 'user_id']
