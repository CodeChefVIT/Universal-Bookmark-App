from rest_framework import serializers
from .models import URLData

class URLDataSerializer(serializers.ModelSerializer):
    class Meta:
        model = URLData
        fields = '__all__'