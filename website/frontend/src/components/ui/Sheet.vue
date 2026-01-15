<template>
  <Teleport to="body">
    <div v-if="isOpen" class="fixed inset-0 z-50" @click.self="close">
      <div class="fixed inset-0 bg-black/20" @click.self="close"></div>
      <div class="fixed inset-y-0 right-0 z-50 w-full sm:w-[400px] border-l-2 border-black bg-white shadow-xl">
        <div class="flex h-full flex-col">
          <div class="flex items-center justify-between border-b-2 border-black p-4">
            <h2 class="text-lg font-semibold">
              <slot name="title" />
            </h2>
            <button
              @click="close"
              class="rounded-md p-1 hover:bg-gray-100 transition-colors"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              >
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
              </svg>
            </button>
          </div>
          <div class="flex-1 overflow-y-auto p-4">
            <slot />
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:modelValue'])

const isOpen = ref(props.modelValue)

watch(() => props.modelValue, (newVal) => {
  isOpen.value = newVal
})

const close = () => {
  isOpen.value = false
  emit('update:modelValue', false)
}
</script>
